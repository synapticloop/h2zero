package com.synapticloop.sample;

import com.synapticloop.h2zero.base.exception.H2ZeroFinderException;
import com.synapticloop.sample.h2zero.mysql.finder.PetFinder;
import com.synapticloop.sample.h2zero.mysql.finder.UserFinder;
import com.synapticloop.sample.h2zero.mysql.model.Pet;
import com.synapticloop.sample.h2zero.mysql.model.User;

import java.sql.SQLException;
import java.util.List;

public class Simple {
	public static void main(String[] args) throws SQLException, H2ZeroFinderException {
		List<User> users = UserFinder.findByFlIsAliveNumAge(true, 1)
				.withLimit(1)
				.withOffset(0)
				.withConnection(null)
				.executeSilent();

		Pet pet = PetFinder.findByPrimaryKey(1L)
				.withConnection(null)
				.execute();
		System.out.println(pet);
	}
}
