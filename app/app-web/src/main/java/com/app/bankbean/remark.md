****************************************************
接口名称         请求Javabean  响应Javabean
****************************************************
查询当日明细  BankBean01In BankBean01Out



****************************************************
**************javabean to xml***********************
BankBeanPubRequest bbpr = new BankBeanPubRequest();
bbpr.setTransCode("0000");
bbpr.setBankCode("1111");
BankBean01In bbi = new BankBean01In();
bbi.setAccNo("1234567890");
bbi.setAreaCode("10");
BankBeanRequest<BankBean01In> bbRequest = new BankBeanRequest<BankBean01In>(bbpr,bbi);
String xml = JaxbUtil.convertToXml(BankBeanRequest.class, bbRequest);
****************************************************
**************xml to javabean***********************
String xmlResponse = "<?xml version=\"1.0\" encoding = \"GBK\"?>\r\n" + 
    			"<CMS>\r\n" + 
    			"<eb>\r\n" + 
    			"    <pub>\r\n" + 
    			"        <TransCode>0000</TransCode>\r\n" + 
    			"        <BankCode>1111</BankCode>\r\n" + 
    			"    </pub>\r\n" + 
    			"    <out>\r\n" + 
    			"        <AccNo>1234567890</AccNo>\r\n" + 
    			"        <AreaCode>10</AreaCode>\r\n" + 
    			"        <rd>\r\n" + 
    			"            <Drcrf>1</Drcrf>\r\n" + 
    			"            <VouhNo>2343264234</VouhNo>\r\n" + 
    			"        </rd>\r\n" + 
    			"        <rd>\r\n" + 
    			"            <Drcrf>2</Drcrf>\r\n" + 
    			"            <VouhNo>867867867</VouhNo>\r\n" + 
    			"        </rd>\r\n" + 
    			"    </out>\r\n" + 
    			"</eb>\r\n" + 
    			"</CMS>";
@SuppressWarnings("unchecked")
BankBeanResponse<BankBean01Out> bbResponse = JaxbUtil.convertToJavaBean(BankBeanResponse.class, xmlResponse);
****************************************************
