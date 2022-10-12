package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	
	//need to inject the session factory
	@Autowired 
	private SessionFactory sessionFactory;
	
	@Override
	//@Transactional move this functionality to service layer to handle
	public List<Customer> getCustomers() {
		// get the current hibernate session
		
		Session currentSession = sessionFactory.getCurrentSession();
		//create a query,sort by last name
		
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by lastName",Customer.class);
		//execute query and get result list
		
		List<Customer> customers = theQuery.getResultList();
		//return result
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		
		//get current hibernate session
		Session session= sessionFactory.getCurrentSession();
		//save or update the customer
		session.saveOrUpdate(customer);
		
	}

	@Override
	public Customer getCustomer(int theId) {
		// get the current hibernate session
		Session session= sessionFactory.getCurrentSession();

		//now retrieve /read from database using the primary key
		Customer customer = session.get(Customer.class, theId);
		return customer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get the current hibernate session
		Session session= sessionFactory.getCurrentSession();
		
		
		//delete the object with primary key
		Query theQuery = session.createQuery("delete from Customer where id=:customerId");
		
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

}
