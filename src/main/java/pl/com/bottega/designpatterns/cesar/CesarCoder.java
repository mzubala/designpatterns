package pl.com.bottega.designpatterns.cesar;

class CesarCoder {

    private final int key;

    private final String alphabet;

    CesarCoder(int key) {
        this.key = key;
        StringBuilder sb = new StringBuilder();
        for (char i = 'a'; i <= 'z'; i++) {
            sb.append(i);
        }
        alphabet = sb.toString();
    }

    char encode(char c) {
        int index = alphabet.indexOf(Character.toLowerCase(c));
        if(index == -1) {
            return c;
        }
        char encoded = alphabet.charAt((index + key) % alphabet.length());
        if(Character.isUpperCase(c)) {
            return Character.toUpperCase(encoded);
        } else {
            return encoded;
        }
    }

    char decode(char c) {
        int index = alphabet.indexOf(Character.toLowerCase(c));
        if(index == -1) {
            return c;
        }
        int decodedIndex = (index - key) % alphabet.length();
        char decoded = alphabet.charAt(decodedIndex < 0 ? alphabet.length() + decodedIndex : decodedIndex);
        if(Character.isUpperCase(c)) {
            return Character.toUpperCase(decoded);
        } else {
            return decoded;
        }
    }

}
