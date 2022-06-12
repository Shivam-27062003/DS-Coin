package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
    if(firstTransaction==null){
      firstTransaction=transaction;
      lastTransaction=transaction;
      numTransactions=1;
    }
    else {
      numTransactions++;
      lastTransaction.next = transaction;
      lastTransaction = transaction;
    }
  }

  public Transaction RemoveTransaction () throws EmptyQueueException {
    if(numTransactions==0){
      throw new EmptyQueueException();
    }
    else if(numTransactions==1){
      Transaction T=firstTransaction;
      firstTransaction=null;
      numTransactions=0;
      lastTransaction=null;
      return T;
    }
    else{
      numTransactions--;
      Transaction T=firstTransaction;
      firstTransaction=firstTransaction.next;
      return T;
    }
  }

  public int size() {
    return numTransactions;
  }
}
