/*FreeMind - A Program for creating and viewing Mindmaps
 *Copyright (C) 2000-2006 Joerg Mueller, Daniel Polansky, Christian Foltin, Dimitri Polivaev and others.
 *
 *See COPYING for Details
 *
 *This program is free software; you can redistribute it and/or
 *modify it under the terms of the GNU General Public License
 *as published by the Free Software Foundation; either version 2
 *of the License, or (at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, write to the Free Software
 *Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*
 * Created on 17.05.2005
 *
 */
package freemind.controller.filter.condition

import freemind.controller.Controller
import freemind.controller.filter.condition.ConditionFactory.Companion.createDescription
import freemind.main.XMLElement
import freemind.modes.MindMapNode
import java.util.Locale

internal class IgnoreCaseNodeContainsCondition(value: String) : NodeCondition() {
    private val value: String

    init {
        this.value = value.lowercase(Locale.getDefault())
    }

    override fun checkNode(c: Controller?, node: MindMapNode?): Boolean {
        return node!!.text.lowercase(Locale.getDefault()).indexOf(value) > -1
    }

    override fun save(element: XMLElement?) {
        val child = XMLElement()
        child.name = NAME
        super.saveAttributes(child)
        child.setAttribute(VALUE, value)
        element!!.addChild(child)
    }

    override fun createDesctiption(): String? {
        val nodeCondition = ConditionFactory.FILTER_NODE.name
        val simpleCondition = ConditionFactory.FILTER_CONTAINS.name
        return createDescription(
            nodeCondition,
            simpleCondition, value, true
        )
    }

    companion object {
        const val VALUE = "value"
        const val NAME = "ignore_case_node_contains_condition"
        fun load(element: XMLElement): Condition {
            return IgnoreCaseNodeContainsCondition(
                element.getStringAttribute(VALUE)
            )
        }
    }
}
