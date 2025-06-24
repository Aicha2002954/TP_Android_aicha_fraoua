package com.example.myapplication.ui.theme.product.cart

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

fun generatePdfReceipt(context: Context, fileName: String, content: String) {
    val document = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = document.startPage(pageInfo)
    val canvas = page.canvas
    val paint = android.graphics.Paint()

    val lines = content.split("\n")
    var yPosition = 25

    for (line in lines) {
        canvas.drawText(line, 10f, yPosition.toFloat(), paint)
        yPosition += 20
    }

    document.finishPage(page)

    val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(directory, "$fileName.pdf")

    try {
        document.writeTo(FileOutputStream(file))
        Toast.makeText(context, "Reçu enregistré : ${file.absolutePath}", Toast.LENGTH_LONG).show()
    } catch (e: Exception) {
        Toast.makeText(context, "Erreur : ${e.message}", Toast.LENGTH_LONG).show()
    }

    document.close()
}
