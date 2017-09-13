// 
// Created by the DataSnap proxy generator.
// 2017/7/27 11:00:38
// 

package com.example.youjingjing.myelectronicbalance;

import com.example.youjingjing.myelectronicbalance.javaandroid.DBXDataTypes;
import com.example.youjingjing.myelectronicbalance.javaandroid.DBXException;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSAdmin;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSHTTPRequestType;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTCommand;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTConnection;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTParamDirection;
import com.example.youjingjing.myelectronicbalance.javaandroid.DSRESTParameterMetaData;
import com.example.youjingjing.myelectronicbalance.javaandroid.TDBXStreamValue;
import com.example.youjingjing.myelectronicbalance.javaandroid.TDataSet;

import java.util.Date;


public class GeneratedProxy {
  public static class TPRT_DS_Interface extends DSAdmin {
    public TPRT_DS_Interface(DSRESTConnection Connection) {
      super(Connection);
    }

    private DSRESTParameterMetaData[] TPRT_DS_Interface_DataSnapTest_Metadata;
    private DSRESTParameterMetaData[] get_TPRT_DS_Interface_DataSnapTest_Metadata() {
      if (TPRT_DS_Interface_DataSnapTest_Metadata == null) {
        TPRT_DS_Interface_DataSnapTest_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("Value", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TPRT_DS_Interface_DataSnapTest_Metadata;
    }

    /**
     * @param Value [in] - Type on server: string
     * @return result - Type on server: string
     */
//    public String DataSnapTest(String Value) throws DBXException {
//      DSRESTCommand cmd = getConnection().CreateCommand();
//      cmd.setRequestType(DSHTTPRequestType.GET);
//      cmd.setText("TPRT_DS_Interface.DataSnapTest");
//      cmd.prepare(get_TPRT_DS_Interface_DataSnapTest_Metadata());
//      cmd.getParameter(0).getValue().SetAsString(Value);
//      getConnection().execute(cmd);
//      return  cmd.getParameter(1).getValue().GetAsString();
//    }
//
//
    private DSRESTParameterMetaData[] TPRT_DS_Interface_GetUserAuthority_Metadata;
    private DSRESTParameterMetaData[] get_TPRT_DS_Interface_GetUserAuthority_Metadata() {
      if (TPRT_DS_Interface_GetUserAuthority_Metadata == null) {
        TPRT_DS_Interface_GetUserAuthority_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("UserName", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "string"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TPRT_DS_Interface_GetUserAuthority_Metadata;
    }

    /**
     * @param UserName [in] - Type on server: string
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetUserAuthority(String UserName) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TPRT_DS_Interface.GetUserAuthority");
      cmd.prepare(get_TPRT_DS_Interface_GetUserAuthority_Metadata());
      cmd.getParameter(0).getValue().SetAsString(UserName);
      getConnection().execute(cmd);

      return (TDataSet) cmd.getParameter(1).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TPRT_DS_Interface_GetPLU_Metadata;
    private DSRESTParameterMetaData[] get_TPRT_DS_Interface_GetPLU_Metadata() {
      if (TPRT_DS_Interface_GetPLU_Metadata == null) {
        TPRT_DS_Interface_GetPLU_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("StoreID", DSRESTParamDirection.Input, DBXDataTypes.Int32Type, "Integer"),
          new DSRESTParameterMetaData("BeginDateTime", DSRESTParamDirection.Input, DBXDataTypes.DateTimeType, "TDateTime"),
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.TableType, "TDataSet"),
        };
      }
      return TPRT_DS_Interface_GetPLU_Metadata;
    }

    /**
     * @param StoreID [in] - Type on server: Integer
     * @param BeginDateTime [in] - Type on server: TDateTime
     * @return result - Type on server: TDataSet
     */
    public TDataSet GetPLU(int StoreID, Date BeginDateTime) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TPRT_DS_Interface.GetPLU");
      cmd.prepare(get_TPRT_DS_Interface_GetPLU_Metadata());
      cmd.getParameter(0).getValue().SetAsInt32(StoreID);
      cmd.getParameter(1).getValue().SetAsDateTime(BeginDateTime);
      getConnection().execute(cmd);
      return (TDataSet) cmd.getParameter(2).getValue().GetAsTable();
    }
    
    
    private DSRESTParameterMetaData[] TPRT_DS_Interface_AS_Execute_Metadata;
    private DSRESTParameterMetaData[] get_TPRT_DS_Interface_AS_Execute_Metadata() {
      if (TPRT_DS_Interface_AS_Execute_Metadata == null) {
        TPRT_DS_Interface_AS_Execute_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("ProviderName", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "WideString"),
          new DSRESTParameterMetaData("CommandText", DSRESTParamDirection.Input, DBXDataTypes.WideStringType, "WideString"),
          new DSRESTParameterMetaData("ParamReader", DSRESTParamDirection.InputOutput, DBXDataTypes.BinaryBlobType, "TDBXStreamValue"),
          new DSRESTParameterMetaData("OwnerDataStream", DSRESTParamDirection.InputOutput, DBXDataTypes.BinaryBlobType, "TDBXStreamValue"),
        };
      }
      return TPRT_DS_Interface_AS_Execute_Metadata;
    }

    /**
     * @param //ProviderName [in] - Type on server: WideString
     * @param //CommandText [in] - Type on server: WideString
     * @param //ParamReader [in/out] - Type on server: TDBXStreamValue
     * @param //OwnerDataStream [in/out] - Type on server: TDBXStreamValue
     */
    public static class AS_ExecuteReturns {
      public TDBXStreamValue ParamReader;
      public TDBXStreamValue OwnerDataStream;
    }
    public AS_ExecuteReturns AS_Execute(String ProviderName, String CommandText, TDBXStreamValue ParamReader, TDBXStreamValue OwnerDataStream) throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.POST);
      cmd.setText("TPRT_DS_Interface.AS_Execute");
      cmd.prepare(get_TPRT_DS_Interface_AS_Execute_Metadata());
      cmd.getParameter(0).getValue().SetAsString(ProviderName);
      cmd.getParameter(1).getValue().SetAsString(CommandText);
      if (ParamReader != null) {
        cmd.getParameter(2).getValue().SetAsDBXValue(ParamReader);
      } else {
        cmd.getParameter(2).getValue().SetTDBXNull("TDBXStreamValue");
      }
      if (OwnerDataStream != null) {
        cmd.getParameter(3).getValue().SetAsDBXValue(OwnerDataStream);
      } else {
        cmd.getParameter(3).getValue().SetTDBXNull("TDBXStreamValue");
      }
      getConnection().execute(cmd);
      AS_ExecuteReturns ret = new AS_ExecuteReturns();
      ret.ParamReader = (TDBXStreamValue)cmd.getParameter(2).getValue().GetAsDBXValue();
      ret.OwnerDataStream = (TDBXStreamValue)cmd.getParameter(3).getValue().GetAsDBXValue();
      return ret;
    }
    
    
    private DSRESTParameterMetaData[] TPRT_DS_Interface_AS_GetProviderNames_Metadata;
    private DSRESTParameterMetaData[] get_TPRT_DS_Interface_AS_GetProviderNames_Metadata() {
      if (TPRT_DS_Interface_AS_GetProviderNames_Metadata == null) {
        TPRT_DS_Interface_AS_GetProviderNames_Metadata = new DSRESTParameterMetaData[]{
          new DSRESTParameterMetaData("", DSRESTParamDirection.ReturnValue, DBXDataTypes.WideStringType, "string"),
        };
      }
      return TPRT_DS_Interface_AS_GetProviderNames_Metadata;
    }

    /**
     * @return result - Type on server: string
     */
    public String AS_GetProviderNames() throws DBXException {
      DSRESTCommand cmd = getConnection().CreateCommand();
      cmd.setRequestType(DSHTTPRequestType.GET);
      cmd.setText("TPRT_DS_Interface.AS_GetProviderNames");
      cmd.prepare(get_TPRT_DS_Interface_AS_GetProviderNames_Metadata());
      getConnection().execute(cmd);
      return  cmd.getParameter(0).getValue().GetAsString();
    }
  }

}
