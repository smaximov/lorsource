/*
 * Copyright 1998-2015 Linux.org.ru
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ru.org.linux.help

import javax.servlet.ServletRequest

import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ExceptionHandler, PathVariable, RequestMapping, ResponseStatus}
import org.springframework.web.servlet.ModelAndView
import ru.org.linux.markdown.MarkdownRenderService
import ru.org.linux.util.RichFuture._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

@Controller
class HelpController @Autowired() (renderService: MarkdownRenderService) extends StrictLogging {
  import HelpController._

  @RequestMapping(Array("/help/{page}"))
  def helpPage(request:ServletRequest, @PathVariable page:String) = {
    val title = HelpPages.getOrElse(page, {
      logger.info(s"Help page not found $page")
      throw new HelpPageNotFoundException()
    })

    val source = IOUtils.toString(request.getServletContext.getResource(s"/help/$page"))

    renderService.render(source, RenderTimeout.fromNow).map { result ⇒
      new ModelAndView("help", Map(
        "title" -> title,
        "helpText" -> result
      ).asJava)
    }.toDeferredResult
  }

  @ExceptionHandler(Array(classOf[HelpPageNotFoundException]))
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleNotFoundException = new ModelAndView("code404")
}

class HelpPageNotFoundException extends RuntimeException

object HelpController {
  private val RenderTimeout = 30.seconds

  val HelpPages = Map(
    "lorcode.md" -> "Разметка сообщений (LORCODE)",
    "rules.md" -> "Правила форума"
  )
}
