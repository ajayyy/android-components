/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package mozilla.components.browser.state.helper

import mozilla.components.browser.state.action.TabListAction
import mozilla.components.browser.state.state.BrowserState
import mozilla.components.browser.state.state.createCustomTab
import mozilla.components.browser.state.state.createTab
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.support.test.ext.joinBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TargetTabTest {
    @Test
    fun lookupInStore() {
        val store = BrowserStore(
            initialState = BrowserState(
                tabs = listOf(
                    createTab("https://www.mozilla.org", id = "mozilla"),
                    createTab("https://www.example.org", id = "example"),
                    createTab("https://theverge.com", id = "theverge", private = true)
                ),
                customTabs = listOf(
                    createCustomTab("https://www.reddit.com/r/firefox/", id = "reddit")
                ),
                selectedTabId = "mozilla"
            )
        )

        assertEquals(
            "https://www.mozilla.org",
            TargetTab.Selected.lookupIn(store)?.content?.url
        )

        assertEquals(
            "https://www.mozilla.org",
            TargetTab.Pinned("mozilla").lookupIn(store)?.content?.url
        )

        assertEquals(
            "https://theverge.com",
            TargetTab.Pinned("theverge").lookupIn(store)?.content?.url
        )

        assertNull(
            TargetTab.Pinned("unknown").lookupIn(store)
        )

        assertNull(
            TargetTab.Pinned("reddit").lookupIn(store)
        )

        assertEquals(
            "https://www.reddit.com/r/firefox/",
            TargetTab.Custom("reddit").lookupIn(store)?.content?.url
        )

        assertNull(
            TargetTab.Custom("unknown").lookupIn(store)
        )

        assertNull(
            TargetTab.Custom("mozilla").lookupIn(store)
        )

        store.dispatch(
            TabListAction.SelectTabAction("example")
        ).joinBlocking()

        assertEquals(
            "https://www.example.org",
            TargetTab.Selected.lookupIn(store)?.content?.url
        )

        store.dispatch(
            TabListAction.RemoveAllTabsAction
        ).joinBlocking()

        assertNull(
            TargetTab.Selected.lookupIn(store)
        )
    }

    @Test
    fun lookupInState() {
        val state = BrowserState(
            tabs = listOf(
                createTab("https://www.mozilla.org", id = "mozilla"),
                createTab("https://www.example.org", id = "example"),
                createTab("https://theverge.com", id = "theverge", private = true)
            ),
            customTabs = listOf(
                createCustomTab("https://www.reddit.com/r/firefox/", id = "reddit")
            ),
            selectedTabId = "mozilla"
        )

        assertEquals(
            "https://www.mozilla.org",
            TargetTab.Selected.lookupIn(state)?.content?.url
        )

        assertEquals(
            "https://www.mozilla.org",
            TargetTab.Pinned("mozilla").lookupIn(state)?.content?.url
        )

        assertEquals(
            "https://theverge.com",
            TargetTab.Pinned("theverge").lookupIn(state)?.content?.url
        )

        assertNull(
            TargetTab.Pinned("unknown").lookupIn(state)
        )

        assertNull(
            TargetTab.Pinned("reddit").lookupIn(state)
        )

        assertEquals(
            "https://www.reddit.com/r/firefox/",
            TargetTab.Custom("reddit").lookupIn(state)?.content?.url
        )

        assertNull(
            TargetTab.Custom("unknown").lookupIn(state)
        )

        assertNull(
            TargetTab.Custom("mozilla").lookupIn(state)
        )
    }
}
