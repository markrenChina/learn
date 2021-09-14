object RegularUtils {

    private const val HTTPS_FROM = "(https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?"

    fun checkUrlFROMHttps(url : String) = url.matches(HTTPS_FROM.toRegex())
}