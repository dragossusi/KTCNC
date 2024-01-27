package com.mindovercnc.linuxcnc.di

import com.squareup.wire.GrpcCall
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ro.dragossusi.proto.linuxcnc.*
import ro.dragossusi.proto.linuxcnc.hal.HalComponent
import ro.dragossusi.proto.linuxcnc.hal.HalPin
import ro.dragossusi.proto.linuxcnc.hal.HalPinValue
import ro.dragossusi.proto.linuxcnc.status.SystemMessage


actual val GrpcModule =
    DI.Module("grpc") {
        bindSingleton<LinuxCncClient> {
            object : LinuxCncClient{
                override fun ReadStatus(): GrpcCall<ReadStatusRequest, CncStatus> {
                    TODO("Not yet implemented")
                }

                override fun ReadError(): GrpcCall<ReadErrorRequest, SystemMessage> {
                    TODO("Not yet implemented")
                }

                override fun GetComponents(): GrpcCall<GetComponentsRequest, GetComponentsResponse> {
                    TODO("Not yet implemented")
                }

                override fun CreateComponent(): GrpcCall<CreateComponentRequest, HalComponent> {
                    TODO("Not yet implemented")
                }

                override fun GetPins(): GrpcCall<HalComponent, GetPinsResponse> {
                    TODO("Not yet implemented")
                }

                override fun CreatePin(): GrpcCall<CreatePinRequest, HalPin> {
                    TODO("Not yet implemented")
                }

                override fun GetPinValue(): GrpcCall<HalPin, HalPinValue> {
                    TODO("Not yet implemented")
                }

                override fun SetTaskMode(): GrpcCall<SetTaskModeRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetTaskState(): GrpcCall<SetTaskStateRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun TaskAbort(): GrpcCall<TaskAbortRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun HomeAxis(): GrpcCall<HomeAxisRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun UnhomeAxis(): GrpcCall<UnhomeAxisRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun OverrideLimits(): GrpcCall<OverrideLimitsRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetMotionMode(): GrpcCall<SetMotionModeRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun JogContinuous(): GrpcCall<JogContinuousRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun JogIncremental(): GrpcCall<JogIncrementalRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun JogAbsolute(): GrpcCall<JogAbsoluteRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun JogStop(): GrpcCall<JogStopRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetMinPositionLimit(): GrpcCall<SetMinPositionPositionLimitRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetMaxPositionLimit(): GrpcCall<SetMaxPositionPositionLimitRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetBacklash(): GrpcCall<SetBacklashRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetFeedHold(): GrpcCall<SetFeedHoldRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun LoadTaskPlan(): GrpcCall<LoadTaskPlanRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun LoadToolTable(): GrpcCall<LoadToolTableRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SendMdiCommand(): GrpcCall<SendMdiCommandRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetAuto(): GrpcCall<SetAutoRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetBlockDelete(): GrpcCall<SetBlockDeleteRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetFeedOverride(): GrpcCall<SetFeedOverrideRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetFlood(): GrpcCall<SetFloodRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetMist(): GrpcCall<SetMistRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetOptionalStop(): GrpcCall<SetOptionalStopRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetRapidOverride(): GrpcCall<SetRapidOverrideRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetSpindle(): GrpcCall<SetSpindleRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }

                override fun SetSpindleOverride(): GrpcCall<SetSpindleOverrideRequest, SendCommandResponse> {
                    TODO("Not yet implemented")
                }
            }
        }
    }