package io.writeopia.intronotes.integrationTests

import io.writeopia.intronotes.dynamo.introNotesTable
import io.writeopia.intronotes.persistence.repository.DynamoIntroNotesRepository
import io.writeopia.intronotes.persistence.repository.INTRO_NOTES_TABLE
import io.writeopia.intronotes.unit.utils.Samples
import io.writeopia.intronotes.write.writeIntroNotes
import org.junit.Assert.*
import org.junit.Test
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

class WriteNotesKtTest {

    @Test
    fun `it should be possible to save note correctly`() {
        val dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .endpointOverride(URI.create("http://localhost:8000"))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("FAKE", "FAKE")
                )
            )
            .build()

        val id = "fakeId"
        val title = "fakeTitle"

        val input = Samples.sampleEntity(id, title)
        val table = introNotesTable(
            tableName = INTRO_NOTES_TABLE,
            dynamoClient = dynamoDbClient
        )

        val result = writeIntroNotes(
            input,
            loggerFn = {},
            loggerErrorFn = {},
            notesTable = table
        )

        assertEquals(200, result.statusCode)
        val note = DynamoIntroNotesRepository.readNote(id, table)

        assertEquals(id, note.id)
    }
}
