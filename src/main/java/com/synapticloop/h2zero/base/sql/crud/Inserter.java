package com.synapticloop.h2zero.base.sql.crud;

import com.synapticloop.h2zero.base.model.ModelBase;

import java.sql.Connection;

public class Inserter {
	private final ModelBase modelBase;
	private Connection connection = null;
	public Inserter(ModelBase modelBase) {
		this.modelBase = modelBase;
	}

	public Inserter withConnection(Connection connection) {
		this.connection = connection;
		return(this);
	}

	public int execute() {
		modelBase.executeInsertInternal()
	}
}
