import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.0)",
    comments = "Source: weather.proto")
public final class WeatherGrpc {

  private WeatherGrpc() {}

  public static final String SERVICE_NAME = "Weather";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<WeatherOuterClass.CountryRequest,
      WeatherOuterClass.ForecastResponse> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribe",
      requestType = WeatherOuterClass.CountryRequest.class,
      responseType = WeatherOuterClass.ForecastResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<WeatherOuterClass.CountryRequest,
      WeatherOuterClass.ForecastResponse> getSubscribeMethod() {
    io.grpc.MethodDescriptor<WeatherOuterClass.CountryRequest, WeatherOuterClass.ForecastResponse> getSubscribeMethod;
    if ((getSubscribeMethod = WeatherGrpc.getSubscribeMethod) == null) {
      synchronized (WeatherGrpc.class) {
        if ((getSubscribeMethod = WeatherGrpc.getSubscribeMethod) == null) {
          WeatherGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<WeatherOuterClass.CountryRequest, WeatherOuterClass.ForecastResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherOuterClass.CountryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  WeatherOuterClass.ForecastResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WeatherMethodDescriptorSupplier("subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WeatherStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherStub>() {
        @java.lang.Override
        public WeatherStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherStub(channel, callOptions);
        }
      };
    return WeatherStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WeatherBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherBlockingStub>() {
        @java.lang.Override
        public WeatherBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherBlockingStub(channel, callOptions);
        }
      };
    return WeatherBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WeatherFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WeatherFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WeatherFutureStub>() {
        @java.lang.Override
        public WeatherFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WeatherFutureStub(channel, callOptions);
        }
      };
    return WeatherFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class WeatherImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribe(WeatherOuterClass.CountryRequest request,
        io.grpc.stub.StreamObserver<WeatherOuterClass.ForecastResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                WeatherOuterClass.CountryRequest,
                WeatherOuterClass.ForecastResponse>(
                  this, METHODID_SUBSCRIBE)))
          .build();
    }
  }

  /**
   */
  public static final class WeatherStub extends io.grpc.stub.AbstractAsyncStub<WeatherStub> {
    private WeatherStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(WeatherOuterClass.CountryRequest request,
        io.grpc.stub.StreamObserver<WeatherOuterClass.ForecastResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class WeatherBlockingStub extends io.grpc.stub.AbstractBlockingStub<WeatherBlockingStub> {
    private WeatherBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<WeatherOuterClass.ForecastResponse> subscribe(
        WeatherOuterClass.CountryRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class WeatherFutureStub extends io.grpc.stub.AbstractFutureStub<WeatherFutureStub> {
    private WeatherFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WeatherFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WeatherFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final WeatherImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(WeatherImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((WeatherOuterClass.CountryRequest) request,
              (io.grpc.stub.StreamObserver<WeatherOuterClass.ForecastResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class WeatherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WeatherBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return WeatherOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Weather");
    }
  }

  private static final class WeatherFileDescriptorSupplier
      extends WeatherBaseDescriptorSupplier {
    WeatherFileDescriptorSupplier() {}
  }

  private static final class WeatherMethodDescriptorSupplier
      extends WeatherBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    WeatherMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WeatherGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WeatherFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
