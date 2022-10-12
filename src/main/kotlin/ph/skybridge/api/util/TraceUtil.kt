package ph.skybridge.api.util

import io.opentracing.Tracer
import org.eclipse.microprofile.opentracing.Traced

@Traced
class TraceUtil {

    companion object {

        /**
         * Provide trace id
         *
         * Utility to provide trace id using the tracer interface
         *
         * @param tracer
         * @return
         */
        fun provideTraceId(tracer: Tracer?): String {
            return tracer?.activeSpan()?.context()?.toTraceId() ?: "no-trace-id"
        }
    }
}