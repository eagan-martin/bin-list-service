package ph.skybridge.api.util

import io.opentracing.Tracer
import org.eclipse.microprofile.opentracing.Traced

@Traced
class TraceUtil {

    companion object {
        fun provideTraceId(tracer: Tracer?): String {
            return tracer?.activeSpan()?.context()?.toTraceId() ?: "no-trace-id"
        }
    }
}