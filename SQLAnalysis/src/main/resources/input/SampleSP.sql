USE [epostrx]
GO

/****** Object:  StoredProcedure [dbo].[P_ansbillccordershippack]    Script Date: 2/18/2016 11:49:21 AM ******/
DROP PROCEDURE [dbo].[P_ansbillccordershippack]
GO

/****** Object:  StoredProcedure [dbo].[P_ansbillccordershippack]    Script Date: 2/18/2016 11:49:21 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

/**
 *
 * Copyright (c) 2001-2015 AdvanceNet Health Solutions, Inc. All Rights Reserved.
 *
 * THIS SOFTWARE IS THE CONFIDENTIAL AND PROPRIETARY INFORMATION OF
 * ADVANCENET SOLUTIONS, INC. ("CONFIDENTIAL INFORMATION").
 * AND IS PROTECTED BY U.S. COPYRIGHT LAW. ANY USER SHALL NOT DISCLOSE
 * SUCH CONFIDENTIAL INFORMATION AND SHALL USE THIS SOFTWARE IN
 * ACCORDANCE WITH THE TERMS OF ANY DISCLOSURES AND/OR LICENSE AGREEMENTS
 * ENTERED INTO WITH ADVANCENET. FAILURE TO ABIDE IS PUNISHABLE BY A COURT OF
 * LAW.
 *-
 **/


/**********************************************************************
 * <b>P_anscheckitembusy</b>
 * 
 * Procedure preps for a final order CC bill from Shippack station
 * vice the workflow
 *
 * <p>=================================================================
 * <br><b>Change History </b><p>
 * 
 *  Date		Name			Description
 *  ----------- --------------- ---------------------------------------
 *	11-Novc-2007	John Strecker Initial Version<br>
 *                                  			 
 * <p>==================================================================
 *
 **********************************************************************/

CREATE PROCEDURE [dbo].[P_ansbillccordershippack]  
(
	@ordernum		numeric(35),

	@processtxn		numeric(1) OUTPUT,
	@tpnum			varchar(35) OUTPUT,
	@glpostnum		varchar(35) OUTPUT,
	@glpostamount		numeric(16) OUTPUT,
	@paymenttxn		varchar(35) OUTPUT,
	@authamount		numeric(16) OUTPUT,
	@authcode		varchar(35) OUTPUT,
	@requestid		varchar(35) OUTPUT,
	@patientnum		varchar(35) OUTPUT,
	@future1		varchar(35) OUTPUT,
	@future2		varchar(35) OUTPUT,
	@future3		varchar(35) OUTPUT,
	@future4		varchar(35) OUTPUT

)
AS
BEGIN

	/*
	** Declarations.
	*/
	DECLARE @tc	INT, 
		@err INT,
		@rc INT,
		@idseq numeric(35),
		@ccorder char(1),
		@billccshippack char(1)

	/*
	** Begin the transaction.
	*/
	SELECT @tc = @@trancount
	IF (@tc > 0)
		SAVE TRAN DB_ansbillccordershippack
	ELSE
		BEGIN TRAN DB_ansbillccordershippack
	BEGIN

	/*
	** ensure we have the correct params passed in. Else return error. 
	*/

	/*
		** ensure we have a script id
		*/
		 if(@ordernum <=0) RETURN -1

		/*
		** default item not running
		*/
		select @processtxn=0		
		select @ccorder='N'

			/*
			** clear cc busy flag
			*/
			update order_header set cc_busy=0 where order_num=@ordernum
			SELECT @rc = @@rowcount, @err = @@error

		/*
		** check if order is cc method
		*/

		IF EXISTS(SELECT 1
				from 
				order_billship (nolock) 
				where 
				payment_card_seq_num IS NOT NULL
				and order_num=@ordernum)
			BEGIN
				select @ccorder='Y'
			END

		SELECT  @billccshippack=bill_cc_shippack
				from order_defaults (nolock)



		if(@ccorder='Y' and @billccshippack='Y')
			BEGIN
				
			/*
			** grab info
			*/
				SELECT  @patientnum = order_payment_owner
					from 
					order_billship (nolock) 
					where order_num=@ordernum

				select @tpnum = trading_partner_num,
					@glpostnum = order_gl_posted,
					@glpostamount = order_total_amount
						from
						order_header (nolock) where order_num=@ordernum							
							
				/*
				** set to BILL
				*/
				Select @paymenttxn= 2
				select @processtxn=1
				 SELECT @authamount= pc_reply_amount,
					@authcode = pc_reply_auth_code,
					@requestid = pc_reply_request_id
           			          FROM payment_card_reply   (nolock)
         				   WHERE pc_reply_seq_num=(Select max(p.pc_reply_seq_num)
          			  		FROM payment_card_reply p (nolock), general_ledger g   (nolock)
          			  		WHERE
           					 p.gl_post_num=g.gl_post_num
           					 and p.pc_reply_type='A'
           					 and p.pc_reply_auth_code IS NOT NULL
            					and g.gl_post_num=@glpostnum)



					/*
					** no auth, do authbill
					*/
					if(@authcode IS NULL)
						BEGIN
							Select @paymenttxn=5
							Select @authamount=0
							select @requestid=null
							select @authcode=null
							select @processtxn=1	
						END

					ELSE
						BEGIN
						/*
						** auth amount and order amount changed, do auth bill
						*/
						if(@glpostamount > @authamount)
							BEGIN
								Select @paymenttxn= 5
								Select @authamount=0
								select @requestid=null
								select @authcode=null
								select @processtxn=1
							END

						-- some providers may not have replyId - so force it
			            if(@requestid IS NULL)
                          BEGIN
                            select @requestid = @authcode
       					  END
						END

						/*
						** if txn is a BILL, make sure it has not been already posted for order
						*/
						if(@paymenttxn=2)
						BEGIN

 							IF EXISTS(SELECT 1
         							   FROM payment_card_reply (nolock)
         							   WHERE pc_reply_seq_num=(Select max(p.pc_reply_seq_num)
        							    FROM payment_card_reply p (nolock), general_ledger g  (nolock)
        							    WHERE
           					 			p.gl_post_num=g.gl_post_num
           								 and p.pc_reply_type='B'
           								 and p.pc_reply_rcode=1
            								and p.order_num=@ordernum))

								BEGIN
									select @processtxn=0
								END
								

						END



			END
/* post amount is zero, then skip */
        if(@glpostamount IS NULL or @glpostamount=0)
             BEGIN
					select @processtxn=0
			END
		END

	/*
	** Check for any errors
	*/
	  IF (@rc <= 0) AND (@err = 0)
	  BEGIN
           select @processtxn=0
	   ROLLBACK TRAN DB_ansbillccordershippack
		RETURN -1
	  END

      IF (@err <> 0)
	  BEGIN
            select @processtxn=0
	    ROLLBACK TRAN  DB_ansbillccordershippack
		RETURN -1
	  END

	IF (@tc = 0)
	  COMMIT TRAN  DB_ansbillccordershippack
	  RETURN 0
END

GO

