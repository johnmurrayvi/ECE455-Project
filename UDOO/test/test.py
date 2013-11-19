#tweepy setup, you must use the keys given to you when you create your app
import tweepy
consumer_key="SnTSSTHtm7GybJp0ap8rQQ"
consumer_secret="zfifM0LBg4e1b9upVSq3HscdBGXP3JbuQ0W4JOEmm0"
access_token="2162925949-LG6Bu2ROllLB6zdN3Bv705PPeiPCmnBHgrlypq3"
access_token_secret="KUXoZ0IclbMM9I0GjY6ft4NRklz8xYWSGckrIPg1WBfsk"
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
