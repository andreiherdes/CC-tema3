package com.cloud.homework.dao;

import java.util.ArrayList;
import java.util.List;

import com.cloud.homework.model.Transaction;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

public class DatastoreDao {

	private Datastore datastore;
	private KeyFactory keyFactory;

	private final String kind = "Transactions";

	public DatastoreDao() {
		datastore = DatastoreOptions.getDefaultInstance().getService(); // Authorized Datastore service
		keyFactory = datastore.newKeyFactory().setKind("Transaction"); // Is used for creating keys later
	}

	private Transaction entityToTransaction(Entity entity) {
		Transaction transaction = new Transaction();

		transaction.setRequest(entity.getString(Transaction.REQUEST));
		transaction.setResponse(entity.getString(Transaction.RESPONSE));

		return transaction;
	}

	public Long createTransaction(Transaction transaction) {
		IncompleteKey key = keyFactory.newKey(); // Key will be assigned once written
		FullEntity<IncompleteKey> incTransactionEntity = Entity.newBuilder(key)
				.set(Transaction.REQUEST, transaction.getRequest()).set(Transaction.RESPONSE, transaction.getResponse())
				.build();
		Entity transactionEntity = datastore.add(incTransactionEntity); // Save the Entity
		return transactionEntity.getKey().getId(); // The ID of the Key
	}

	public Transaction readTransaction(Long transactionId) {
		Entity transactionEntity = datastore.get(keyFactory.newKey(transactionId)); // Load an Entity for Key(id)
		return entityToTransaction(transactionEntity);
	}

	public List<Transaction> getAllTransactions() {
		Query<Entity> query = Query.newEntityQueryBuilder() // Build the Query
				.setKind(kind).build();
				
		QueryResults<Entity> resultList = datastore.run(query); // Run the query

		List<Transaction> transactions = new ArrayList<>();
		
		while (resultList.hasNext()) { 								  // We still have data
			transactions.add(entityToTransaction(resultList.next())); // Add the Transaction to the List
		}

		return transactions;
	}

}
