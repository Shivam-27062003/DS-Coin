package DSCoinPackage;

import HelperClasses.*;

public class BlockChain_Malicious {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock[] lastBlocksList;

  public static boolean checkTransactionBlock (TransactionBlock tB) {
    String s="";
    if(tB.previous==null){s=start_string;}
    else{
      CRF c=new CRF(64);
      s=c.Fn(tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce);
    }

    MerkleTree M=new MerkleTree();
    String summary=M.Build(tB.trarray);

    boolean B=true;
    for(int i=0;i<tB.trarray.length;i++){
      if(tB.checkTransaction(tB.trarray[i])==false){B=false;break;}
    }
    if(zeros(tB.dgst)==true && tB.dgst==s && tB.trsummary==summary && B==true)return true;
    return false;
  }

  public TransactionBlock FindLongestValidChain () {
    return null;
  }

  public void InsertBlock_Malicious (TransactionBlock newBlock) {

  }

  public static boolean zeros(String S){
    if(S.charAt(0)=='0' && S.charAt(1)=='0' && S.charAt(2)=='0' && S.charAt(3)=='0')return true;
    return false;
  }
}
