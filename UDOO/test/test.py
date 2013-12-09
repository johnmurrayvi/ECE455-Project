#tweepy setup, you must use the keys given to you when you create your app
import tweepy
consumer_key="your_key"
consumer_secret="your_secret"
access_token="your_token"
access_token_secret="your_secret_token"
#"logs in" to twitter,
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

#try:
#  redirect_url = auth.get_authorization_url ()
#except tweepy.TweepError:
#  print 'Error - Failed to get request token'

#print 'Auth: ' + redirect_url

#v = raw_input('PIN:').strip ()
#auth.get_access_token (v)
#print "ACCESS_KEY = '%s'" % auth.access_token.key
#print "ACCESS_SECRET = '%s'" % auth.access_token.secret

api.update_status ('Auto Garden - This is a tweet from the Automated Gardening Environment project')
public_tweets = api.user_timeline()
for tweet in public_tweets:
    print tweet.text
