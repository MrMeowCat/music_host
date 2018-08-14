package com.github.mrmeowcat.music_host.config

import com.couchbase.client.java.Bucket
import com.couchbase.client.java.env.CouchbaseEnvironment
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories

/**
 * Couchbase data source configuration.
 */
@Configuration
@EnableReactiveCouchbaseRepositories
@EnableCouchbaseAuditing
class CouchbaseConfig : AbstractCouchbaseConfiguration() {

    @Value("\${spring.couchbase.bootstrap-hosts}")
    private lateinit var hosts: MutableList<String>

    @Value("\${spring.couchbase.bucket.name}")
    private lateinit var bucketName: String

    @Value("\${spring.couchbase.bucket.password}")
    private lateinit var bucketPassword: String

    @Value("\${spring.couchbase.env.timeouts.connect}")
    private val connectTimeout: Long = 0

    override fun getBucketPassword(): String {
        return bucketPassword
    }

    override fun getBucketName(): String {
        return bucketName
    }

    override fun getBootstrapHosts(): MutableList<String> {
        return hosts
    }

    override fun couchbaseEnvironment(): CouchbaseEnvironment {
        return DefaultCouchbaseEnvironment.builder()
                .connectTimeout(connectTimeout)
                .build()
    }

    /**
     * Creates primary index if not exist. Required to execute queries in repositories.
     */
    override fun couchbaseClient(): Bucket {
        val bucket: Bucket = super.couchbaseClient()
        bucket.bucketManager().createN1qlPrimaryIndex("idx", true, false)
        return bucket
    }
}
