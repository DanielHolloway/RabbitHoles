module rabbitholes.usecase {
	exports com.rabbitholes.usecase;
	exports com.rabbitholes.usecase.exception;
	exports com.rabbitholes.usecase.port;

	requires rabbitholes.domain;
	requires org.apache.commons.lang3;
}
