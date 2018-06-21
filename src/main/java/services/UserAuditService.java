package services;

public interface UserAuditService {
    Boolean saveAuditAction(String userId, String Action) throws Exception;
    Boolean saveAuditActionAsync(String userId, String Action) throws Exception;
}
