module rabbitholes.spring {
	requires rabbitholes.config;
	requires rabbitholes.usecase;
	requires rabbitholes.controller;
	requires spring.web;
	requires spring.beans;

	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires com.fasterxml.jackson.databind;
	requires jackson.annotations;

	exports com.rabbitholes.spring;
	exports com.rabbitholes.spring.config;
	opens com.rabbitholes.spring.config to spring.core;
}
