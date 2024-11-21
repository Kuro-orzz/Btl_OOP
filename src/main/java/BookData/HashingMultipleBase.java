package BookData;

import java.util.ArrayList;
import java.util.List;

public class HashingMultipleBase {
    private final long base1 = 31;
    private final long base2 = 47;
    private final long mod1 = 1000000007;
    private final long mod2 = 1000000009;
    private final List<Long> hash1 = new ArrayList<>();
    private final List<Long> hash2 = new ArrayList<>();
    private final List<Long> pow1 = new ArrayList<>();
    private final List<Long> pow2 = new ArrayList<>();

    public HashingMultipleBase() {}

    public HashingMultipleBase(int strLength) {
        pow1.add(1L);
        for (int i = 1; i <= strLength; i++) {
            pow1.add((pow1.get(i-1) * base1) % mod1);
        }
        pow2.add(1L);
        for (int i = 1; i <= strLength; i++) {
            pow2.add((pow2.get(i-1) * base2) % mod2);
        }
    }

    public List<Long> hash(String info, int option) {
        String hashInfo = info.toLowerCase();
        if (option == 1) {
            hash1.add(0L);
            for (int i = 0; i < hashInfo.length(); i++) {
                hash1.add((hash1.get(i) * base1 + (int) hashInfo.charAt(i)) % mod1);
            }
            return hash1;
        } else if (option == 2) {
            hash2.add(0L);
            for (int i = 0; i < hashInfo.length(); i++) {
                hash2.add((hash2.get(i) * base2 + (int) hashInfo.charAt(i)) % mod2);
            }
            return hash2;
        }
        return null;
    }

    public long hashInfo(String info, int option) {
        String hashInfo = info.toLowerCase();
        long h = 0;
        if (option == 1) {
            for (int i = 0; i < hashInfo.length(); i++) {
                h = (h * base1 + (int) hashInfo.charAt(i)) % mod1;
            }
        } else if (option == 2) {
            for (int i = 0; i < hashInfo.length(); i++) {
                h = (h * base2 + (int) hashInfo.charAt(i)) % mod2;
            }
        }
        return h;
    }

    public long getHash(int l, int r, int option) {
        if (option == 1) {
            return (hash1.get(r) - hash1.get(l - 1) * pow1.get(r - l + 1) + mod1 * mod1) % mod1;
        } else if (option == 2) {
            return (hash2.get(r) - hash2.get(l - 1) * pow2.get(r - l + 1) + mod2 * mod2) % mod2;
        }
        return 0;
    }
}