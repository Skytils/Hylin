package skytils.hylin.request

class HypixelAPIException(endpoint: String, error: String) :
    Exception("Error '$error' caught while requesting from '$endpoint'")