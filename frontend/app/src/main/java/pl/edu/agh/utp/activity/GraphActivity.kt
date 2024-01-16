package pl.edu.agh.utp.activity;

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.utp.R

class GraphActivity : AppCompatActivity() {

@SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_graph)

            val webView = findViewById<WebView>(R.id.graphWebView)

        // Włącz obsługę JavaScript w WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Ładuj kod Kotlin/JS do WebView
        val html = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>Kotlin/JS in WebView</title>
                    <script type="text/javascript" src="data:text/javascript;base64,${getJsCodeBase64()}"></script>
                </head>
                <body>
                    <script type="text/javascript">
                        document.getElementById("output").innerHTML = getHelloMessage();
                    </script>
                </body>
            </html>
        """


//        webView.loadDataWithBaseURL("https://observablehq.com/@d3/disjoint-force-directed-graph/2?intent=fork")
        webView.loadUrl("https://observablehq.com/@d3/disjoint-force-directed-graph/2?intent=fork")
        }

private fun getJsCodeBase64(): String {
        val jsCode = """
            function getHelloMessage() {
                return "nigger on the trigger";
            }
        """.trimIndent()

        return android.util.Base64.encodeToString(jsCode.toByteArray(), android.util.Base64.NO_WRAP)
        }
        }