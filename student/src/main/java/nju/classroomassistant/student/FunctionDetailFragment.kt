package nju.classroomassistant.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nju.classroomassistant.student.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_function_detail.*
import kotlinx.android.synthetic.main.function_detail.view.*

/**
 * A fragment representing a single Function detail screen.
 * This fragment is either contained in a [FunctionListActivity]
 * in two-pane mode (on tablets) or a [FunctionDetailActivity]
 * on handsets.
 */
class FunctionDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.content
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.function_detail, container, false)

        // Show the dummy content as text in a TextView.
        item?.let {
            rootView.function_detail.text = it.details
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
