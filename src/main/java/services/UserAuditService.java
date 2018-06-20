package services;

public interface UserAuditService {
    Boolean saveAuditAction(String userId, String Action) throws Exception;
}
