package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
    CRF c = new CRF(64);
    if (lastBlock == null) {
      newBlock.nonce=findNonce(newBlock);
      newBlock.dgst=c.Fn(start_string+"#"+newBlock.trsummary+"#"+newBlock.nonce);
      lastBlock=newBlock;
      tr_count = 1;
    } else {
      newBlock.previous = lastBlock;
      newBlock.nonce = findNonce(newBlock);
      newBlock.dgst = c.Fn(lastBlock.dgst + "#" + newBlock.trsummary + "#" + newBlock.nonce);
      lastBlock = newBlock;
    }
  }

  public String findNonce(TransactionBlock T){
    String s="1000000001";
    CRF c=new CRF(64);
    if(lastBlock==null){
      while(zeros(c.Fn(start_string+"#"+T.trsummary+"#"+s))==false){
        int i=Integer.parseInt(s);
        i++;
        s=Integer.toString(i);
      }
    }
    else {
      while (zeros(c.Fn(T.previous.dgst + "#" + T.trsummary + "#" + s)) == false) {
        int i = Integer.parseInt(s);
        i++;
        s = Integer.toString(i);
      }
    }

    return s;
  }

  public boolean zeros(String S){
    if(S.charAt(0)=='0' && S.charAt(1)=='0' && S.charAt(2)=='0' && S.charAt(3)=='0')return true;
    return false;
  }
}
