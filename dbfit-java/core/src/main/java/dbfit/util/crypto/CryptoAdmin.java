package dbfit.util.crypto;

import java.io.File;

public class CryptoAdmin {

    private static CryptoKeyStoreManagerFactory ksManagerFactory = null;
    private static CryptoKeyServiceFactory keyFactory = null;
    private static CryptoServiceFactory cryptoServiceFactory = null;

    public static void setKSManagerFactory(CryptoKeyStoreManagerFactory factory) {
        ksManagerFactory = factory;
    }

    public static void setCryptoKeyServiceFactory(CryptoKeyServiceFactory factory) {
        keyFactory = factory;
    }

    public static void setCryptoServiceFactory(CryptoServiceFactory factory) {
        cryptoServiceFactory = factory;
    }


    public static CryptoKeyStoreManagerFactory getKSManagerFactory() {
        if (null == ksManagerFactory) {
            return new JKSCryptoKeyStoreManagerFactory();
        }

        return ksManagerFactory;
    }

    public static CryptoKeyServiceFactory getCryptoKeyServiceFactory() {
        if (null == keyFactory) {
            return new JKSCryptoKeyServiceFactory(getDefaultKeyStoreLocation());
        }

        return keyFactory;
    }

    public static CryptoServiceFactory getCryptoServiceFactory() {
        if (null == cryptoServiceFactory) {
            cryptoServiceFactory = new AESCryptoServiceFactory(
                    getCryptoKeyServiceFactory().getKeyService());
        }

        return cryptoServiceFactory;
    }

    public static File getDefaultKeyStoreLocation() {
        String ksLocation = System.getProperty("dbfit.keystore.path");
        if (ksLocation == null) {
            ksLocation = System.getProperty("user.home");
        }

        return new File(ksLocation);
    }

    /*** Shortcut Methods ***/
    public static CryptoService getCryptoService() {
        return getCryptoServiceFactory().getCryptoService();
    }


}

