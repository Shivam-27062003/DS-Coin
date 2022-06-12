package DSCoinPackage;

import java.util.*;
import HelperClasses.*;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSObj) {
   if(mycoins.size()>0) {
    Pair<String, TransactionBlock> P=new Pair<String,TransactionBlock>("",null);
    P = mycoins.get(0);
    mycoins.remove(0);
    Transaction T = new Transaction();
    T.coinID = P.first;
    T.Source = this;
    T.coinsrc_block = P.second;
    T.Destination.UID = destUID;
    int n = in_process_trans.length;
    for (int i = 0; i < n; i++) {
     if (in_process_trans[i] == null) {
      in_process_trans[i] = T;
      break;
     }
    }
    DSObj.pendingTransactions.AddTransactions(T);
   }
  }


  public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
   if(DSObj.bChain.lastBlock==null)throw new MissingTransactionException();
   TransactionBlock TB=DSObj.bChain.lastBlock;
   List<Pair<String,String>> L1=new ArrayList<Pair<String,String>>();
   List<Pair<String,String>> L2=new ArrayList<Pair<String,String>>();
   while(TB!=null){
    if(find(TB,tobj)){break;}
    else{
     TB=TB.previous;
    }
   }
   if(TB==null)throw new MissingTransactionException();
   L1=QueryDocument(tobj,TB);

   TransactionBlock tB=DSObj.bChain.lastBlock;
   while(tB!=TB){
    Pair<String,String> P=new Pair<String,String>("","");
    P.first=tB.dgst;
    P.second=tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce;
    L2.add(P);
    tB=tB.previous;
   }
   L2.add(new Pair<String,String>(tB.dgst,tB.previous.dgst+"#"+tB.trsummary+"#"+tB.nonce));
   L2.add(new Pair<String,String>(tB.previous.dgst,null));

   List<Pair<String,String>> L3=new ArrayList<Pair<String,String>>();
   for(int i=L2.size()-1;i>=0;i--){
    L3.add(L2.get(i));
   }

   Pair<List<Pair<String,String>>,List<Pair<String,String>>> ans=new Pair<List<Pair<String,String>>,List<Pair<String,String>>>(L1,L3);

   int m=in_process_trans.length;
   int k=0;
   for(int i=0;i<m;i++){
    if(in_process_trans[i]==tobj){
     k=i;
     break;
    }
   }

   for(int i=k;i<m-1;i++){
    in_process_trans[i]=in_process_trans[i+1];
   }

   return ans;
  }

  public boolean find(TransactionBlock TB,Transaction T){
   if(TB.trarray.length==0)return false;
   boolean b=false;
   int n=TB.trarray.length;
   for(int i=0;i<n;i++){
    if(TB.trarray[i]==T){
     b=true;
     break;
    }
   }
   return b;
  }

  public void MineCoin(DSCoin_Honest DSObj) {

  }  

  public void MineCoin(DSCoin_Malicious DSObj) {

  }


  public List<Pair<String,String>> QueryDocument(Transaction T,TransactionBlock Tb){
   // Implement Code here
   int doc_idx=0;
   int n=Tb.trarray.length;
   for(int i=0;i<n;i++){
    if(Tb.trarray[i]==T){
     doc_idx=i;
     break;
    }
   }


   ArrayList<Pair<String, String>> arr = new ArrayList<Pair<String, String>>();
   TreeNode node=Tb.Tree.rootnode;
   int i = doc_idx+1;
   int k = n;
   List<Pair<String, String>> ans = new ArrayList<Pair<String, String>>();
   ans.add(new Pair<String, String>(node.val, null));
   while (node.left != null) {
    ans.add(new Pair<String, String>(node.left.val, node.right.val));
    if (i <= k) {
     node = node.left;
     k = k - k / 2;
    } else {
     node = node.right;
     k = k + k / 2;
    }
   }
   ArrayList<Pair<String, String>> fin = new ArrayList<Pair<String, String>>();
   for (int j=ans.size()-1;j>=0;j--)
   {
    fin.add(ans.get(j));
   }
   return fin;
  }

}
