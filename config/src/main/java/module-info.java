module rabbitholes.config {
	exports com.rabbitholes.config;

	requires rabbitholes.usecase;
	requires rabbitholes.domain;
	requires rabbitholes.jug;
	requires rabbitholes.uuid;
	requires rabbitholes.db.simple;
	requires rabbitholes.db.hazelcast;
}
