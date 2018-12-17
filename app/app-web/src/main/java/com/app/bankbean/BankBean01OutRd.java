package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)  
public class BankBean01OutRd {
	
	@XmlElement(name="Drcrf")        
	private String drcrf;            //借贷标志               
	@XmlElement(name="VouhNo")       
	private String vouhNo;           //凭证号              
	@XmlElement(name="Amount")       
	private String amount;           //发生额              
	@XmlElement(name="RecipBkNo")    
	private String recipBkNo;        //对方行号            
	@XmlElement(name="RecipAccNo")   
	private String recipAccNo;       //对方账号            
	@XmlElement(name="RecipName")    
	private String recipName;        //对方户名            
	@XmlElement(name="Summary")      
	private String summary;          //摘要                
	@XmlElement(name="UseCN")        
	private String useCN;            //用途                
	@XmlElement(name="PostScript")   
	private String postScript;       //附言                
	@XmlElement(name="Ref")          
	private String ref;              //业务编号            
	@XmlElement(name="BusCode")      
	private String busCode;          //业务代码            
	@XmlElement(name="Oref")         
	private String oref;             //相关业务编号        
	@XmlElement(name="EnSummary")    
	private String enSummary;        //英文备注            
	@XmlElement(name="BusType")      
	private String busType;          //业务种类            
	@XmlElement(name="CvouhType")    
	private String cvouhType;        //凭证种类            
	@XmlElement(name="AddInfo")      
	private String addInfo;          //附加信息            
	@XmlElement(name="TimeStamp")    
	private String timeStamp;        //时间戳              
	@XmlElement(name="RepReserved3") 
	private String repReserved3;     //电子回单唯一标识    
	@XmlElement(name="RepReserved4") 
	private String repReserved4;     //财务公司二级账户信息
	@XmlElement(name="UpDtranf")     
	private String upDtranf;         //冲正标志            
	@XmlElement(name="ValueDate")    
	private String valueDate;        //起息日              
	@XmlElement(name="TrxCode")      
	private String trxCode;          //工行交易代码        
	@XmlElement(name="SequenceNo")   
	private String sequenceNo;       //主机交易流水号      
	@XmlElement(name="Cashf")        
	private String cashf;            //交易类型            
	@XmlElement(name="CASHEXF")      
	private String cASHEXF;          //钞汇标志            
	@XmlElement(name="Remark")       
	private String remark;           //备注                
	@XmlElement(name="TradeDate")    
	private String tradeDate;        //交易日期            
	@XmlElement(name="TradeTime")    
	private String tradeTime;        //交易时间            
	@XmlElement(name="TradeLocation")
	private String tradeLocation;    //交易场所            
	@XmlElement(name="TradeFee")     
	private String tradeFee;         //手续费              
	@XmlElement(name="ExRate")       
	private String exRate;           //汇率                
	@XmlElement(name="ForCurrtype")  
	private String forCurrtype;      //外币币种            
	@XmlElement(name="EnAbstract")   
	private String enAbstract;       //英文摘要            
	@XmlElement(name="RecBankName")  
	private String recBankName;      //对方行名            
	@XmlElement(name="OpenBankNo")   
	private String openBankNo;       //开户行行号          
	@XmlElement(name="OpenBankBIC")  
	private String openBankBIC;      //开户行BIC           
	@XmlElement(name="OpenBankName") 
	private String openBankName;     //开户银行名称        
	@XmlElement(name="SubAcctSeq")   
	private String subAcctSeq;       //账号序号            
	@XmlElement(name="THCurrency")   
	private String tHCurrency;       //币种                
	@XmlElement(name="OnlySequence") 
	private String onlySequence;     //银行交易流水号      

}
