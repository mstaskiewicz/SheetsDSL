package xyz.sheetsdsl.examples

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes.SPREADSHEETS
import com.google.api.services.sheets.v4.model.Spreadsheet
import com.google.common.io.Resources
import xyz.sheetsdsl.SpreadsheetDsl
import java.awt.Desktop
import java.io.File
import java.net.URI


object SheetsService {

    private val httpTransport by lazy { GoogleNetHttpTransport.newTrustedTransport() }
    private val jsonFactory: JsonFactory by lazy { GsonFactory.getDefaultInstance() }
    private val credential by lazy { credential(httpTransport) }

    private val sheets: Sheets by lazy {
        Sheets.Builder(httpTransport, jsonFactory, credential)
            .setApplicationName("SheetsDSL")
            .build()
    }

    fun create(spreadsheet: Spreadsheet): Spreadsheet =
        sheets.spreadsheets().create(spreadsheet).execute()

    private fun credential(httpTransport: NetHttpTransport): Credential {
        val credentialUrl = Resources.getResource(
            SheetsService::class.java, "/credentials.json"
        )

        val clientSecrets = GoogleClientSecrets.load(
            jsonFactory, credentialUrl.openStream().bufferedReader()
        )

        val flow = GoogleAuthorizationCodeFlow.Builder(
            httpTransport, jsonFactory, clientSecrets, listOf(SPREADSHEETS)
        )
            .setDataStoreFactory(FileDataStoreFactory(File("tokens")))
            .setAccessType("offline")
            .build()

        val receiver = LocalServerReceiver.Builder().setPort(8888).build()

        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }
}

fun spreadsheet(
    title: String? = null,
    init: SpreadsheetDsl.() -> Unit,
): Spreadsheet =
    SheetsService.create(SpreadsheetDsl(title).apply(init).build())

fun Spreadsheet.openInBrowser() {
    Desktop.getDesktop().browse(URI.create(checkNotNull(spreadsheetUrl) {
        "Missing spreadsheetUrl!"
    }))
}
