package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {


  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

  TransactionBlock(Transaction[] t) {
    for(int i=0;i<t.length;i++){
      t[i].coinsrc_block=this;
    }
    trarray=t;
    previous=null;
    Tree.Build(trarray);
    trsummary=Tree.rootnode.val;
    dgst=null;
  }

  public boolean checkTransaction (Transaction t) {
    int count=0;
    if(t.coinsrc_block==null)return true;
    TransactionBlock T=t.coinsrc_block;
    int n=T.trarray.length;
    for(int i=0;i<n;i++){
      if(t.coinID==trarray[i].coinID && t.Source==trarray[i].Destination){
        return false;
      }
    }
    boolean b=true;
    TransactionBlock curr=this;
    while(curr!=T){
      if(find(T,t)){
        b=false;
        break;
      }
      else{
        curr=curr.previous;
      }
    }
    return b;

  }

  public boolean find(TransactionBlock TB, Transaction T){
    int n=TB.trarray.length;
    boolean b=false;
    for(int i=0;i<n;i++){
      if(TB.trarray[i].coinID==T.coinID){
        b=true;
        break;
      }
    }
    return b;
  }
}
