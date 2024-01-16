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
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Interactive Graph</title>
    <script type="text/javascript" src="https://unpkg.com/vis-network/standalone/umd/vis-network.min.js"></script>
</head>
<body>
    <div id="graph-container" style="height: 100vh;"></div>

    <script type="text/javascript">
        var nodes = new vis.DataSet([
          { id: 1, label: "Node 1" },
          { id: 2, label: "Node 2" },
          { id: 3, label: "Node 3" },
          { id: 4, label: "Node 4" },
          { id: 5, label: "Node 5" },
        ]);

        var edges = new vis.DataSet([
          { from: 1, to: 2, label: 'Edge 1' },
          { from: 2, to: 3, label: 'Edge 2' },
          { from: 3, to: 4, label: 'Edge 3' },
          { from: 4, to: 5, label: 'Edge 4' },
        ]);

        var data = {
            nodes: nodes,
            edges: edges
        };

        var options = {
            layout: {
                hierarchical: false
            },
            edges: {
                arrows: {
                    to: {
                        enabled: true,
                        scaleFactor: 0.8,
                    }
                },
                font: {
                    size: 30,
                    align: "middle"
                },
                color: "#000000"
            },
            nodes: {
                color: {
                    background: "#ffffff"
                },
                font: {
                    size: 30
                }
            },
            interaction: {
                dragNodes: true,
                dragView: true,
                zoomView: true
            },
            physics: {
                enabled: false
            }
        };

        var container = document.getElementById("graph-container");
        var network = new vis.Network(container, data, options);
    </script>
</body>
</html>

        """


        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "")


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