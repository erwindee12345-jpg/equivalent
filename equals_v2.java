import java.util.ArrayList;
import java.util.List;

public final class equals_v2 {
    private record Account(String user, String role) {}

    private static Account parse(String line) {
        String[] parts = line.split(":", 2);
        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new IllegalArgumentException("invalid record");
        }
        return new Account(parts[0].trim(), parts[1].trim());
    }

    static boolean canDelete(Account account) {
        return "admin".equals(account.role());
    }

    static boolean canRead(Account account) {
        return switch (account.role()) {
            case "admin", "editor", "viewer" -> true;
            default -> false;
        };
    }

    private static void show(Account account) {
        System.out.printf("%-6s role=%-7s read=%-5b delete=%b%n",
            account.user(), account.role(), canRead(account), canDelete(account));
    }

    public static void main(String[] args) {

        List<Account> accounts = new ArrayList<>();
        for (String line : List.of("alice:admin", "bob:editor", "carol:viewer")) {
            accounts.add(parse(line));
        }
        for (Account account : accounts) {
            show(account);
        }
        show(new Account("dave", "admin"));
    }
}