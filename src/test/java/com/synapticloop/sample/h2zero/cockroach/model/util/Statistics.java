package com.synapticloop.sample.h2zero.cockroach.model.util;

//        - - - - thoughtfully generated by synapticloop h2zero - - - -        
//          with the use of synapticloop templar templating language
//            (/java/model/java-create-model-statistics.templar)

import com.synapticloop.sample.h2zero.cockroach.model.UserUser;
import com.synapticloop.sample.h2zero.cockroach.model.Pet;
import com.synapticloop.sample.h2zero.cockroach.model.UserUserPet;
import com.synapticloop.sample.h2zero.cockroach.model.AllTypes;

import java.lang.StringBuilder;

public class Statistics {
	public static String getHitCountJson() {
		return("[" +
			UserUser.getHitCountJson() +
			", " +
			Pet.getHitCountJson() +
			", " +
			UserUserPet.getHitCountJson() +
			", " +
			AllTypes.getHitCountJson() +
		"]");
	}
}