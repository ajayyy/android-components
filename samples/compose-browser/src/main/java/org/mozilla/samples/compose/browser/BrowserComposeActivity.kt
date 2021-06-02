/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.samples.compose.browser

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import components
import mozilla.components.browser.state.helper.TargetTab
import mozilla.components.compose.browser.toolbar.BrowserToolbar
import mozilla.components.compose.engine.WebContent

/**
 * Ladies and gentleman, the browser. ¯\_(ツ)_/¯
 */
class BrowserComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                BrowserToolbar(components.store, components.sessionUseCases)
                WebContent(
                    components.engine,
                    components.store,
                    TargetTab.Selected
                )
            }
        }
    }
}
