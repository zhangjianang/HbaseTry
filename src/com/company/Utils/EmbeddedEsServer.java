package com.company.Utils;

public class EmbeddedEsServer {

//    private static final String DEFAULT_DATA_DIRECTORY = "target/elasticsearch-data";
//
//    private final Node node;
//    private final String dataDirectory;
//
//    public EmbeddedEsServer() {
//        this(DEFAULT_DATA_DIRECTORY);
//    }
//
//    public EmbeddedEsServer(String dataDirectory) {
//        this.dataDirectory = dataDirectory;
//
//        Settings.Builder elasticsearchSettings = Settings.builder()
//                .put("http.enabled", "false")
//                .put("path.data", dataDirectory)
//                .put("path.home", dataDirectory);
//
//        node = new Node(elasticsearchSettings.build());
//
//                .local(true)
//                .settings(elasticsearchSettings.build())
//                .node();
//    }
//
//    public Client getClient() {
//        return node.client();
//    }
//
//    public void shutdown() {
//        try {
//            node.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        deleteDataDirectory();
//    }
//
//    private void deleteDataDirectory() {
//        try {
//            FileUtils.deleteDirectory(new File(dataDirectory));
//        } catch (IOException e) {
//            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
//        }
//    }
}