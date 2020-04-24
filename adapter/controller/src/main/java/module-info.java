module rabbitholes.controller {
	exports com.rabbitholes.controller;
	exports com.rabbitholes.controller.model;

	requires rabbitholes.usecase;
	requires rabbitholes.domain;
}
