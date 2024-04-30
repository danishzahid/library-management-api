package in.cs50.minor1.repository;

import in.cs50.minor1.model.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn,Integer> {
    Txn findByTxnId(String txnId);
}
