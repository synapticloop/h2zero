package com.synapticloop.sample;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.sample.h2zero.mysql.finder.PetFinder;
import com.synapticloop.sample.h2zero.mysql.model.Pet;

import java.sql.SQLException;

public class Simple {
	public static void main(String[] args) throws SQLException, H2ZeroFinderException {
		Pet pet = PetFinder.findByPrimaryKey(1L)
				.withConnection(null)
				.execute();
		System.out.println(pet);
	}
}
