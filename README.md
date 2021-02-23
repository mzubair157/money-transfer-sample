<h3>A sample money transfer app</h3><br/>
<p>Run com.test.money.transfer.core.Main class to launch app.</p>

Use following APIs to use it;</br>
<b>CREATE MONEY TRANSFER</b></br>
<i>POST localhost:4567/api/v1/money/transfers</i>

<b>MARK MONEY TRANSFER AS COMPLETE</b></br>
PUT localhost:4567/api/v1/money/transfers

<b>GET ALL MONEY TRANSFER FOR A CUSTOMER</b></br>
<i>GET localhost:4567/api/v1/money/transfers?senderIdentificationNumber={customerIdentificationNumber}</i>