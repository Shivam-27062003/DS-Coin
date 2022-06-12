package DSCoinPackage;

import HelperClasses.*;
import DSCoinPackage.*;

public class Moderator
 {

  public Members M;

  public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {

   String cID = "";
   Transaction[] T = new Transaction[coinCount];
   int cid = 100000;
   for (int i = 0; i < coinCount; i++) {
    Transaction t = new Transaction();
    cID = Integer.toString(cid);
    t.coinID = cID;
    M.UID="Moderator";
    t.Source = this.M;
    t.coinsrc_block = null;
    T[i] = t;
    cid++;
    i++;
   }

   int m = DSObj.bChain.tr_count;
   int n = coinCount / m;

   for (int i = 0; i < n; i++) {
    Transaction[] arr = new Transaction[m];
    for (int j = 0; j < m; j++) {
     arr[j] = T[i * m + j];
    }
    TransactionBlock TB = new TransactionBlock(arr);
    DSObj.bChain.InsertBlock_Malicious(TB);
   }

   int i = 0;
   while (i < coinCount) {
    int j = 0;
    int k = DSObj.memberlist.length;
    while (j < k) {
     Pair<String, TransactionBlock> P = new Pair<String, TransactionBlock>(T[i].coinID, T[i].coinsrc_block);
     DSObj.memberlist[j].mycoins.add(P);
     Transaction Tr=T[i];
     Tr.Destination = DSObj.memberlist[j];
     DSObj.latestCoinID = Tr.coinID;
     i++;
     j++;
    }
   }

  }
    
  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {

   String cID = "";
   Transaction[] T = new Transaction[coinCount];
   int cid = 100000;
   for (int i = 0; i < coinCount; i++) {
    Transaction t = new Transaction();
    cID = Integer.toString(cid);
    t.coinID = cID;
    M.UID="Moderator";
    t.Source = this.M;
    t.coinsrc_block = null;
    T[i] = t;
    cid++;
    i++;
   }

   int m = DSObj.bChain.tr_count;
   int n = coinCount / m;

   for (int i = 0; i < n; i++) {
    Transaction[] arr = new Transaction[m];
    for (int j = 0; j < m; j++) {
     arr[j] = T[i * m + j];
    }
    TransactionBlock TB = new TransactionBlock(arr);
    DSObj.bChain.InsertBlock_Honest(TB);
   }


   int i = 0;
   while (i < coinCount) {
    int j = 0;
    int k = DSObj.memberlist.length;
    while (j < k) {
     Pair<String, TransactionBlock> P = new Pair<String, TransactionBlock>(T[i].coinID, T[i].coinsrc_block);
     DSObj.memberlist[j].mycoins.add(P);
     Transaction Tr=T[i];
     Tr.Destination=DSObj.memberlist[j];
     DSObj.latestCoinID = Tr.coinID;
     i++;
     j++;
    }
   }

  }
}
