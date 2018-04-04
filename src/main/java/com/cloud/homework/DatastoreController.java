package com.cloud.homework;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.homework.dao.DatastoreDao;
import com.cloud.homework.model.Transaction;

@RestController
public class DatastoreController {

	private DatastoreDao datastoreDao = new DatastoreDao();

	@GetMapping("/datastore")
	public String getAll() {

		List<Transaction> requests = datastoreDao.getAllTransactions();

		String output = "The translations made: " + requests.toString();

		return output;
	}

	@GetMapping("/datastore/{id}")
	public String translate(@PathVariable("id") long id ) {
		Transaction transaction = datastoreDao.readTransaction(id);
		
		return transaction.toString();
	}
}
