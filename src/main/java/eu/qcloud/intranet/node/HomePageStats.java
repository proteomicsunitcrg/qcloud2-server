package eu.qcloud.intranet.node;

public class HomePageStats {
    private Long totalFiles;
    private Long totalLs;
    private Long totalNodes;

    public HomePageStats() {
    }

    public HomePageStats(Long totalFiles, Long totalLs, Long totalNodes) {
        this.totalFiles = totalFiles;
        this.totalLs = totalLs;
        this.totalNodes = totalNodes;
    }

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public Long getTotalLs() {
        return totalLs;
    }

    public void setTotalLs(Long totalLs) {
        this.totalLs = totalLs;
    }

    public Long getTotalNodes() {
        return totalNodes;
    }

    public void setTotalNodes(Long totalNodes) {
        this.totalNodes = totalNodes;
    }
}
