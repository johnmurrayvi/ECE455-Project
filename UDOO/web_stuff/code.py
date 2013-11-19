"""
  code.py

  Initial web server taken from web.py site tutorial

  Initial version: 11/7/13
  Author: Jamie Finney
"""

import web
import page_gen

render = web.template.render ('./templates/', base='layout')

urls = ( '/test', 'gen',
         '/', 'index',
         '/log', 'log')

class index:
  def GET (self):
    return render.index ()

class gen:
  def GET (self):
    post = page_gen.get_page ()
    return render.post (post)

class log:
  def GET (self):
    post = page_gen.get_log ()
    return render.log (post)

if __name__ == "__main__":
  app = web.application (urls, globals ())
  app.run ()

