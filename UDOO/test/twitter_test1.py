from twitter import * 
import os
#import twitter
#import twitter.oauth_dance

CONSUMER_KEY = 'SnTSSTHtm7GybJp0ap8rQQ'
CONSUMER_SECRET = 'zfifM0LBg4e1b9upVSq3HscdBGXP3JbuQ0W4JOEmm0'

oauth_filename = os.path.join (os.path.expanduser ('~'), '.twitterdemo_oauth')

if not os.path.exists (oauth_filename):
  oauth_dance ('Automated Greenhouse Environment', CONSUMER_KEY, CONSUMER_SECRET, oauth_filename)

(oauth_token, oauth_token_secret) = read_token_file (oauth_filename)

auth = OAuth(oauth_token, oauth_token_secret, CONSUMER_KEY, CONSUMER_SECRET)
twitter = Twitter (auth=auth)

# Tweet a new status update
twitter.statuses.update (status = "Hello, World! This is my first tweet.")

# Display all my tweets
for tweet in twitter.statuses.user_timeline ():
  print('Created at',tweet['created_at'])
  print(tweet['text'])
  print('-'*80)

