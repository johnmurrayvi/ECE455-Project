import web

urls = ( '/', 'index' )

app = web.application (urls, globals ())
render = web.template.render ('templates/')

class index (object):
  def GET (self):
    greeting = 'Hello, world!'
#    return "Hello, world!\nI am a boring website."
    return render.index (greeting = greeting)

if __name__ == "__main__":
#  app = web.application (urls, globals ())
  app.run ()

