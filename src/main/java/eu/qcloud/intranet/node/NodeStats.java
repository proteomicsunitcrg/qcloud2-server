package eu.qcloud.intranet.node;

public class NodeStats {

    private Long totalFiles;
    private Long files1Month;
    private Long files6Month;
    private Long files1Week;

    public Long getTotalFiles() {
        return totalFiles;
    }

    public void setTotalFiles(Long totalFiles) {
        this.totalFiles = totalFiles;
    }

    public Long getFiles1Month() {
        return files1Month;
    }

    public void setFiles1Month(Long files1Month) {
        this.files1Month = files1Month;
    }

    public Long getFiles6Month() {
        return files6Month;
    }

    public void setFiles6Month(Long files6Month) {
        this.files6Month = files6Month;
    }

    public Long getFiles1Week() {
        return files1Week;
    }

    public void setFiles1Week(Long files1Week) {
        this.files1Week = files1Week;
    }

    public NodeStats() {
    }

    public NodeStats(Long totalFiles, Long files1Month, Long files6Month, Long files1Week) {
        this.totalFiles = totalFiles;
        this.files1Month = files1Month;
        this.files6Month = files6Month;
        this.files1Week = files1Week;
    }

}